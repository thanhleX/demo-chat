import { Component } from '@angular/core';
import { User } from '../../../core/interfaces/user';
import { UserService } from '../../../core/services/user.service';
import { MessageRoomService } from '../../../core/services/message-room.service';
import { MessageRoom } from '../../../core/interfaces/message-room';
import { MessageContentService } from '../../../core/services/message-content.service';
import { MessageContent, MessageType } from '../../../core/interfaces/message-content';
import { Router } from '@angular/router';
import { MessageRoomMemberService } from '../../../core/services/message-room-member.service';
import { MessageRoomMember } from '../../../core/interfaces/message-room-member';
import { ThemeService } from '../../../core/services/theme.service';

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
  messageToSend: MessageContent = {};
  messageRooms: MessageRoom[] = [];

  themeMode: boolean = this.themeService.themeMode === 'dark' ? true : false;
  themeColor = this.themeService.getGetThemeColorObject(this.themeService.themeColor);
  themeColors = this.themeService.themeColors;

  constructor(
    public userService: UserService,
    private messageRoomService: MessageRoomService,
    private messageContentService: MessageContentService,
    private route: Router,
    private messageRoomMemberService: MessageRoomMemberService,
    private themeService: ThemeService) { }

  ngOnInit() {
    this.currentUser = this.userService.getFromLocalStoge();
    this.userService.connect(this.currentUser);
    this.messageContentService.connect(this.currentUser);
    window.addEventListener('beforeunload', () => {
      this.userService.disconnect(this.currentUser);
    });
    this.findChatRoomAtLeastOneContent();
    this.subscribeMessages();
  }

  ngOnDestroy() {
    this.userService.disconnect(this.currentUser);
    this.messageContentService.disconnect();
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
              this.messageRooms.push(createdMessageRoom);
              this.selectMessageRoom(createdMessageRoom);
            },
            error: (err) => console.log(err)
          });
        } else {
          const room = this.messageRooms.filter(r => r.id === foundMessageRoom.id)[0];
          if (room)
            this.selectMessageRoom(room);
          else {
            this.messageRooms.push(foundMessageRoom);
            this.selectMessageRoom(foundMessageRoom);
          }
        }
      },
      error: (err) => console.log(err)
    });
  }

  selectMessageRoom(room: MessageRoom) {
    console.log(room);
    if (this.selectedMessageRoom.id)
      this.updateLastSeen(this.selectedMessageRoom.id, this.currentUser.username);

    this.selectedMessageRoom = room;
    if (this.selectedMessageRoom.id)
      this.updateLastSeen(this.selectedMessageRoom.id, this.currentUser.username);

    this.getMessagesByRoomId();
  }

  getMessagesByRoomId() {
    this.messageContentService.getMessagesByRoomId(this.selectedMessageRoom.id).subscribe({
      next: (messages: MessageContent[]) => {
        this.selectedMessageRoom.messages = messages;
        this.scrollToBottom();
      }, error: (error: any) => {
        console.log(error);
      }
    });
  }

  subscribeMessages() {
    this.messageContentService.subscribeMessagesObservable().subscribe({
      next: (messageContent: MessageContent) => {
        // this.selectedMessageRoom.messages?.push(messageContent);
        if (messageContent.messageRoomId === this.selectedMessageRoom.id) {
          this.selectedMessageRoom.lastMessage = messageContent;
          this.selectedMessageRoom.messages?.push(messageContent);
          this.scrollToBottom();
        } else {
          const roomToPush = this.messageRooms?.filter(r => r.id === messageContent.messageRoomId)[0];
          if (roomToPush) {
            roomToPush.lastMessage = messageContent;
            roomToPush.unseenCount = (roomToPush.unseenCount ?? 0) + 1;
            this.messageRooms = this.messageRooms.filter(r => r.id !== messageContent.messageRoomId);
            this.messageRooms.unshift(roomToPush);
          } else {
            this.messageRoomService.findById(messageContent.messageRoomId).subscribe({
              next: (room: MessageRoom) => {
                room.lastMessage = messageContent;
                room.unseenCount = 1;
                this.messageRooms.unshift(room);
              }, error: (error: any) => {
                console.log(error);
              }
            });
          }
        }
      }, error: (error: any) => {
        console.log(error);
      }
    });
  }

  sendMessage() {
    this.messageToSend = {
      content: this.messageToSend.content,
      messageRoomId: this.selectedMessageRoom.id,
      sender: this.currentUser.username,
      messageType: MessageType.TEXT
    }

    this.messageContentService.sendMessage(this.messageToSend);

    console.log('this.messageToSend', this.messageToSend);

    this.messageToSend = {};
  }

  logout() {
    this.userService.disconnect(this.currentUser);
    this.messageContentService.disconnect();
    this.userService.removeFromLocalStorage();
    this.route.navigate(['/login']);
  }

  updateLastSeen(roomId?: string, username?: string) {
    this.messageRoomMemberService.updateLastSeen(roomId, username).subscribe({
      next: (member: MessageRoomMember) => {
        this.selectedMessageRoom.unseenCount = 0;
      }, error: (error: any) => {
        console.log(error);
      }
    });
  }

  findChatRoomAtLeastOneContent() {
    // find room at least one content
    if (!this.currentUser.username) return;
    this.messageRoomService.findChatRoomAtLeastOneContent(this.currentUser.username).subscribe({
      next: (rooms: MessageRoom[]) => {
        console.log('rooms', rooms);
        this.messageRooms = rooms;
      },
      error: (err) => console.log(err)
    });
  }

  scrollToBottom() {
    setTimeout(() => {
      const chat = document.getElementById('chat-area');
      if (chat) chat.scrollTop = chat.scrollHeight;
    }, 100);
  }

  switchMode(mode: string) {
    this.themeService.switchMode(mode);
  }

  switchColor(color: string) {
    this.themeService.switchColor(color);
  }
}
