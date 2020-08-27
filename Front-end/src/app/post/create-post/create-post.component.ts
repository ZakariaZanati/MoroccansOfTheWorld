import { Component, OnInit } from '@angular/core';
import {CreatePostPayload} from './create-poste.payload';
import {PostService} from '../../shared/post.service';
import { FormGroup, Validators, FormControl, FormBuilder } from '@angular/forms';
import { throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  createPostForm: FormGroup;
  file : File;

  constructor(private postService:PostService,private formBuilder : FormBuilder,private http:HttpClient) {
    
  }

  ngOnInit(): void {
  
    this.createPostForm = this.formBuilder.group({
      postImage : [''],
      description : ['']
    })
  }

  public onFileChanged(event) {
    //Select File
    this.file = event.target.files[0];
    this.createPostForm.get('postImage').setValue(this.file);
  }

  public createPost(){
    const uploadPost = new FormData();
    uploadPost.append('postImage',this.createPostForm.get('postImage').value);
    uploadPost.append('postDescription',this.createPostForm.get('description').value);
    console.log(uploadPost.getAll('postImage'))
    this.http.post<any>('http://localhost:8181/api/posts/',uploadPost).subscribe(data=>{
      console.log(data)
    },err=>{
      console.log(err)
    });
  }

}
