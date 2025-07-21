import { NgModule } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { MessageModule } from 'primeng/message';



@NgModule({
  declarations: [],
  exports: [
    ButtonModule,
    InputTextModule,
    PasswordModule,
    MessageModule,
  ]
})
export class PrimengModule { }
