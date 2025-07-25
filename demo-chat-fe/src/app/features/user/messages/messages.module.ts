import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MessagesRoutingModule } from './messages-routing.module';
import { MessagesComponent } from './messages.component';
import { ActiveUsersListComponent } from './active-users-list/active-users-list.component';
import { SharedModule } from '../../shared/shared.module';
import { SelectUsersDialogComponent } from './select-users-dialog/select-users-dialog.component';
import { PrimengModule } from '../../../primeng/primeng.module';
import { FormsModule } from '@angular/forms';
import { ConversationsListComponent } from './conversations-list/conversations-list.component';
import { ConversationComponent } from './conversation/conversation.component';
import { CoreModule } from '../../../core/core.module';


@NgModule({
  declarations: [
    MessagesComponent,
    ActiveUsersListComponent,
    SelectUsersDialogComponent,
    ConversationsListComponent,
    ConversationComponent
  ],
  imports: [
    CommonModule,
    MessagesRoutingModule,
    SharedModule,
    PrimengModule,
    FormsModule,
    CoreModule,
  ]
})
export class MessagesModule { }
