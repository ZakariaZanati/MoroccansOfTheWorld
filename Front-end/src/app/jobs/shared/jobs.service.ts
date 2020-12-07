import { Injectable } from '@angular/core';
import { JobModel } from '../job.model';
import {HttpClient, HttpParams} from '@angular/common/http'
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JobsService {

  constructor(private http : HttpClient) { }

  createJobOffer(jobModel : JobModel){
    return this.http.post('http://localhost:8181/api/jobs/',jobModel,{responseType : "text"});
  }

  getJobs():Observable<JobModel[]>{
    return this.http.get<JobModel[]>('http://localhost:8181/api/jobs/');
  }

  previewJobs():Observable<JobModel[]>{
    return this.http.get<JobModel[]>('http://localhost:8181/api/jobs/preview/');
  }

  getJob(id : number):Observable<JobModel>{
    return this.http.get<JobModel>('http://localhost:8181/api/jobs/'+id);
  }

  findJobsByNameAndLocation(name : string, location : string):Observable<JobModel[]>{
    let params = new HttpParams();
    params = params.append('name',name);
    params = params.append('location',location);
    return this.http.get<JobModel[]>('http://localhost:8181/api/jobs/search',{params : params});
  }

  
}
