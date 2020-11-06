import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import {AuthService} from '../auth/shared/auth.service'
import {ResumeServiceService} from './resume-service.service';

@Component({
  selector: 'app-resume',
  templateUrl: './resume.component.html',
  styleUrls: ['./resume.component.css']
})
export class ResumeComponent implements OnInit {


  @Input() userId : number;

  current : boolean = false;
  selectedFiles: FileList;
  currentFile: File;
  progress = 0;
  message = '';
  fileInfos : Observable<any>;

  constructor(private authService : AuthService,private fileService : ResumeServiceService) {}

  ngOnInit(): void {
    this.authService.getCurrentUserInfo().subscribe(data => {
      if (data.userId == this.userId) {
        this.current = true;
      }
    })
    this.fileInfos = this.fileService.getResume(this.userId);
  }

  selectFile(event){
    this.selectedFiles = event.target.files;
    console.log(this.selectedFiles);
  }

  upload(){
    this.progress = 0;
    this.currentFile = this.selectedFiles.item(0);
    this.fileService.uploadResume(this.currentFile,this.userId).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress = Math.round(100*event.loaded/event.total);
      } else if (event instanceof HttpResponse) {
        this.message = event.body.message
        this.fileInfos = this.fileService.getResume(this.userId);
      }
    },err => {
      this.progress = 0;
      this.message = 'Could not upload the file';
      this.currentFile = undefined;
    })

    this.selectedFiles = undefined;
  }

}
