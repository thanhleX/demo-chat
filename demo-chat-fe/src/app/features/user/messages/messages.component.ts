import { Component } from '@angular/core';
import { User } from '../../../core/interfaces/user';
import { UserService } from '../../../core/services/user.service';
import { MessageRoomService } from '../../../core/services/message-room.service';
import { MessageRoom } from '../../../core/interfaces/message-room';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrl: './messages.component.scss'
})
export class MessagesComponent {
  currentUser: User = {};
  activeUsersSubscription: any;
  isShowDialogChat: boolean = false;
  selectedMessageRoom: MessageRoom = {};

  constructor(private userService: UserService, private messageRoomService: MessageRoomService) { }

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

  chat(selectedUsers: User[]) {
    console.log(selectedUsers);
    this.isShowDialogChat = false;

    const usernames = selectedUsers.map(u => u.username).filter((u): u is string => u !== undefined);
    if (this.currentUser.username) usernames.push(this.currentUser.username);

    this.messageRoomService.findMessageRoomByMembers(usernames).subscribe({
      next: (foundMessageRoom: MessageRoom) => {
        console.log('foundMessageRoom', foundMessageRoom);
        // not found
        if (!foundMessageRoom) {
          if (!this.currentUser.username) return;
          // create 
          this.messageRoomService.createChatRoom(this.currentUser.username, usernames).subscribe({
            next: (createdMessageRoom: MessageRoom) => {
              console.log('createdMessageRoom', createdMessageRoom);

            },
            error: (err) => console.log(err)
          });
        }
      },
      error: (err) => console.log(err)
    });
  }

  selectMessageRoom(room: MessageRoom) {
    console.log(room);
    this.selectedMessageRoom = room;
  }
}
