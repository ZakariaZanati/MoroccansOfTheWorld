import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {NotificationModel} from './notification.payload';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  constructor(private http : HttpClient) { }

  getNotifications():Observable<Array<NotificationModel>>{
    return this.http.get<Array<NotificationModel>>('http://localhost:8181/api/notifications');
  }

  setNotificationSeen(id : number){
    console.log(id)
    return this.http.get('http://localhost:8181/api/notifications/'+id);
  }
}
