import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MessageRoom } from '../../../../core/interfaces/message-room';
import { MessageRoomService } from '../../../../core/services/message-room.service';
import { User } from '../../../../core/interfaces/user';

@Component({
  selector: 'app-conversations-list',
  templateUrl: './conversations-list.component.html',
  styleUrl: './conversations-list.component.scss'
})
export class ConversationsListComponent {

  @Input() currentUser: User = {};
  @Input() selectedMessageRoomId: string | undefined = '';

  @Output() selectRoom = new EventEmitter<MessageRoom>();

  messageRooms: MessageRoom[] = [];

  constructor(private messageRoomService: MessageRoomService) { }

  ngOnInit() {
    this.findChatRoomAtLeastOneContent();
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
}
