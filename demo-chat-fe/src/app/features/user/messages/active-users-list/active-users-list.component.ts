import { Component, Input } from '@angular/core';
import { Status, User } from '../../../../core/interfaces/user';
import { UserService } from '../../../../core/services/user.service';
import { lastValueFrom } from 'rxjs';

@Component({
  selector: 'app-active-users-list',
  templateUrl: './active-users-list.component.html',
  styleUrl: './active-users-list.component.scss'
})
export class ActiveUsersListComponent {

  @Input() currentUser: User = {};

  activeUsers: User[] = [];
  activeUsersSubscription: any;

  constructor(private userService: UserService) { }

  async ngOnInit() {
    this.activeUsers = await lastValueFrom(this.userService.getOnlineUsers());
    this.activeUsers?.forEach((u: User) => {
      if (u.username)
        this.userService.activeUsers[u.username] = 'ONLINE';
    })
    this.subscribeActiveUsers();
  }

  ngOnDestroy() {
    this.activeUsersSubscription?.unsubscribe();
  }

  subscribeActiveUsers() {
    this.activeUsersSubscription = this.userService.subscribeActiveUsers().subscribe({
      next: (user: User) => {
        console.log(user);
        if (user.status === Status.OFFLINE) {
          this.activeUsers = this.activeUsers.filter((u: User) => u.username !== user.username);
          if (user.username) delete this.userService.activeUsers[user.username];
        } else if (!this.activeUsers.some(existingUser => existingUser.username === user.username)) {
          this.activeUsers = [...this.activeUsers, user];
          if (user.username) this.userService.activeUsers[user.username] = 'ONLINE';
        }
      },
      error(err) {
        console.log(err);
      }
    });
  }
}
