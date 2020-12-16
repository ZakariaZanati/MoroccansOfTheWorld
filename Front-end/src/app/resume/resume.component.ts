import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import {AuthService} from '../auth/shared/auth.service'
import {ResumeServiceService} from './resume-service.service';

@Component({
  selector: 'app-resume',
  templateUrl: './resume.component.html',
  styleUrls: ['./resume.component.css']
})
export class ResumeComponent implements OnInit {


  @Input() id : number;

  current : boolean = false;
  selectedFiles: FileList;
  currentFile: File;
  progress = 0;
  message = '';
  fileInfos : string = undefined;

  constructor(private authService : AuthService,private fileService : ResumeServiceService,private router:Router) {}

  ngOnInit(): void {
    this.authService.getCurrentUserInfo().subscribe(data => {
      if (data.userId == this.id) {
        this.current = true;
      }
    })
    this.fileService.getResume(this.id).subscribe(data => this.fileInfos = data.toString());
  }

  selectFile(event){
    this.selectedFiles = event.target.files;
    console.log(this.selectedFiles);
  }

  upload(){
    this.progress = 0;
    this.currentFile = this.selectedFiles.item(0);
    this.fileService.uploadResume(this.currentFile,this.id).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.progress = Math.round(100*event.loaded/event.total);
      } else if (event instanceof HttpResponse) {
        this.message = event.body.message
        this.fileService.getResume(this.id).subscribe(data => this.fileInfos = data.toString());
      }
    },err => {
      this.progress = 0;
      this.message = 'Could not upload the file';
      this.currentFile = undefined;
    })

    this.selectedFiles = undefined;
  }

  downloadFile(){
    window.location.href=this.fileInfos
  }

}
