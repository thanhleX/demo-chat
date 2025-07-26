import { Component, Input } from '@angular/core';
import { MessageRoom } from '../../../../core/interfaces/message-room';
import { User } from '../../../../core/interfaces/user';

@Component({
  selector: 'app-conversation',
  templateUrl: './conversation.component.html',
  styleUrl: './conversation.component.scss'
})
export class ConversationComponent {
  
  @Input() room: MessageRoom = {};
  @Input() currentUser: User = {};
  @Input() selectedMessageRoomId: string | undefined = '';
}
