import { Component, Input } from '@angular/core';
import { MessageContent } from '../../../../core/interfaces/message-content';
import { User } from '../../../../core/interfaces/user';

@Component({
  selector: 'app-message-content',
  templateUrl: './message-content.component.html',
  styleUrl: './message-content.component.scss'
})
export class MessageContentComponent {
  @Input() messageContent?: MessageContent;
  @Input() currentUser?: User;
}
