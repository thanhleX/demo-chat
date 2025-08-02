import { Component, Input } from '@angular/core';
import { MessageRoom } from '../../../../core/interfaces/message-room';
import { User } from '../../../../core/interfaces/user';

@Component({
  selector: 'app-message-contents-list',
  templateUrl: './message-contents-list.component.html',
  styleUrl: './message-contents-list.component.scss'
})
export class MessageContentsListComponent {
  @Input() selectedMessageRoom?: MessageRoom;
  @Input() currentUser?: User;
}
