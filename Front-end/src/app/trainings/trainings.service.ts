import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {CourseModel} from './training.payload';
import {CourseResponse} from './training-response';

@Injectable({
  providedIn: 'root'
})
export class TrainingsService {

  constructor(private http:HttpClient) {
    
   }

  createCourse(course : FormData){
      return this.http.post('http://localhost:8181/api/courses',course).subscribe(data => {
        console.log(data);
        console.log("Course created");
      },err => {
        throwError(err);
      });
    }

  getCourse(id : number):Observable<CourseModel>{
    return this.http.get<CourseModel>('http://localhost:8181/api/courses/'+id)
  }

  getCoursesPage(pageNumber : number,pageSize : number,name : string,category : string,date : Date):Observable<CourseResponse>{
    let params = new HttpParams();
    if (!date) {
      params.append('date',"");
    }
    else {
      params = params.append('date',date.toString());
    }
    params = params.append('page',pageNumber.toString());
    params = params.append('size', pageSize.toString());
    params = params.append('name',name);
    params = params.append('category',category);

    return this.http.get<CourseResponse>('http://localhost:8181/api/courses',{params:params});
  }
}
