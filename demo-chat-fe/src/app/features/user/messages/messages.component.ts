import { Component } from '@angular/core';
import { User } from '../../../core/interfaces/user';
import { UserService } from '../../../core/services/user.service';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrl: './messages.component.scss'
})
export class MessagesComponent {
  /**
   * 1. connect /api/ws
   * 2. subscribe /topic/active
   * 3. send connect to others /app/user/connect
   */

  currentUser: User = {};
  activeUsersSubscription: any;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.currentUser = this.userService.getFromLocalStoge();
    this.userService.connect(this.currentUser);
    window.addEventListener('beforeunload', () => {
      this.userService.disconnect(this.currentUser);
    });
  }

  ngOnDestroy() {
    this.userService.disconnect(this.currentUser);
  }
}
