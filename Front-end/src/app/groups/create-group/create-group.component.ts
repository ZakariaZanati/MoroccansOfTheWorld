import { Component, OnInit } from '@angular/core';
import { FormGroup ,FormBuilder, Validators} from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import {GroupService} from '../group.service';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.css']
})
export class CreateGroupComponent implements OnInit {

  show : boolean = true;
  createGroupForm : FormGroup;
  file : File;
  image : any = '';

  public imagePath;
  imgURL: any;
  public message: string;

  constructor(private formBuilder : FormBuilder,private http:HttpClient,
    private groupService : GroupService,public _DomSanitizationService: DomSanitizer) { }

  ngOnInit(): void {
    this.createGroupForm = this.formBuilder.group({
      groupeImg : [''],
      name : [''],
      description : ['']
    })
  }

  public onFileChanged(event) {
    //Select File
    this.file = event.target.files[0];
    this.createGroupForm.get('groupeImg').setValue(this.file);
    //this.image = "data:image/jpeg;base64,"+this.file;
  }
  preview(files) {
    if (files.length === 0)
      return;
 
    var mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      this.message = "Only images are supported.";
      return;
    }
 
    var reader = new FileReader();
    this.imagePath = files;
    reader.readAsDataURL(files[0]); 
    reader.onload = (_event) => { 
      this.imgURL = reader.result; 
    }
  }

  cancel(){
    this.show = !this.show
  }

  createGroup(){
    const uploadGroup = new FormData();
    uploadGroup.append('groupImage',this.createGroupForm.get('groupeImg').value);
    uploadGroup.append('name',this.createGroupForm.get('name').value);
    uploadGroup.append('description',this.createGroupForm.get('description').value);
    this.groupService.createGroup(uploadGroup);
    //window.location.reload();
  }

}
