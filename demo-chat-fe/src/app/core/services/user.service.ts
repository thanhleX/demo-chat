import { Injectable } from '@angular/core';
import { User } from '../interfaces/user';
import { Observable, Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { CompatClient, Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = environment.apiUrl + environment.apiVersion + 'users';
  private wsUrl = environment.apiUrl + environment.webSocketUrl;
  private stompClient: CompatClient = {} as CompatClient;
  private subscriptionActiveUsers: any;
  private activeUsersSubject = new Subject<User>();

  constructor(private http: HttpClient) {
    console.log(environment);
  }

  login(user: User): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
  }

  saveToLocalStorage(user: User) {
    localStorage.setItem('user', JSON.stringify({
      username: user.username,
      avatarUrl: user.avatarUrl,
    }));
  }

  getFromLocalStoge(): User {
    return JSON.parse(localStorage.getItem('user') ?? '{}');
  }

  connect(user: User) {
    const socket = new SockJS(this.wsUrl);
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect(
      {},
      () => this.onConnect(user),
      (err: any) => console.log(err)
    );
  }

  private onConnect(user: User) {
    this.subscribeActive();
    this.sendConnect(user);
  }

  private subscribeActive() {
    this.subscriptionActiveUsers = this.stompClient.subscribe('/topic/active', (message: any) => {
      const user = JSON.parse(message.body);
      console.log(user);
      this.activeUsersSubject.next(user);
    });
  }

  sendConnect(user: User) {
    this.stompClient.send(
      '/app/user/connect',
      {},
      JSON.stringify(user)
    );
  }

  subscribeActiveUsers(): Observable<User> {
    return this.activeUsersSubject.asObservable();
  }
}
