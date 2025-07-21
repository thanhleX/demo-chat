import { Component } from '@angular/core';
import { User } from '../../core/interfaces/user';
import { UserService } from '../../core/services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  user: User = {
    username: '',
    password: '',
  }

  constructor(private userService: UserService, private router: Router) { }

  login() {
    if (!this.user.username || !this.user.password) return;

    this.user.username = this.user.username.trim();
    this.user.password = this.user.password.trim();

    this.userService.login(this.user).subscribe({
      next: (res: User) => {
        console.log(res);
        this.userService.saveToLocalStorage(res);
        this.router.navigate(['/'])
      },
      error: (err: any) => {
        console.log(err);
      }
    });
  }
}
