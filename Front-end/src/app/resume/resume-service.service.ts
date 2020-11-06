import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpHeaders, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs'

@Injectable({
  providedIn: 'root'
})
export class ResumeServiceService {

  constructor(private http:HttpClient) { }

  uploadResume(file : File,userId : number):Observable<HttpEvent<any>>{
    const formData = new FormData();
    formData.append('file',file);
    const req = new HttpRequest('POST','http://localhost:8181/file/upload/'+userId,formData, {
      reportProgress : true,
      responseType : 'json'
    });
    
    return this.http.request(req);
  }

  getResume(userId : number):Observable<any>{
    return this.http.get('http://localhost:8080/file/'+userId);
  }
}
