import { Component, EventEmitter, Input, Output } from '@angular/core';
import { User } from '../../../../core/interfaces/user';
import { UserService } from '../../../../core/services/user.service';

@Component({
  selector: 'app-select-users-dialog',
  templateUrl: './select-users-dialog.component.html',
  styleUrl: './select-users-dialog.component.scss'
})
export class SelectUsersDialogComponent {

  @Input() visible: boolean = false;
  @Input() currentUsername: string | undefined = '';

  @Output() onHideEvent = new EventEmitter();
  @Output() onSubmitEvent = new EventEmitter();

  selectedUsers: User[] = [];
  searchedUsers: User[] = [];

  constructor(private userService: UserService) { }

  onSearch(event: any) {
    this.userService.searchUsersByUsername(event.query).subscribe({
      next: (users) => {
        const selectedUsername = this.selectedUsers.map(u => u.username);

        this.searchedUsers = users.filter(u => {
          return !selectedUsername.includes(u.username);
        });
      },
      error: (err) => console.log(err)
    });
  }

  onHide() {
    this.onHideEvent.emit();
  }

  onSubmit() {
    this.onSubmitEvent.emit(this.selectedUsers);
    this.selectedUsers = [];
  }
}
