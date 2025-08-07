import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { MessageRoomMember } from '../interfaces/message-room-member';

@Injectable({
  providedIn: 'root'
})
export class MessageRoomMemberService {

  private apiUrl = environment.apiUrl + environment.apiVersion + 'message-room-members';

  constructor(private http: HttpClient) { }

  updateLastSeen(roomId?: string, username?: string): Observable<MessageRoomMember> {
      const url = this.apiUrl + '/update-last-seen/' + roomId + '/' + username;
      return this.http.post<MessageRoomMember>(url, {});
    }
}
