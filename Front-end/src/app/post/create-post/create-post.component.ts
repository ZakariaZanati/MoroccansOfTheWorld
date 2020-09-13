import { Component, OnInit } from '@angular/core';
import {CreatePostPayload} from './create-poste.payload';
import {PostService} from '../../shared/post.service';
import { FormGroup, Validators, FormControl, FormBuilder } from '@angular/forms';
import { throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Router,ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  createPostForm: FormGroup;
  file : File;
  id : number;

  public imagePath;
  imgURL: any;
  public message: string;

  constructor(private postService:PostService,private formBuilder : FormBuilder,private http:HttpClient,
    private router: Router,private activatedRoute: ActivatedRoute) {
    
  }

  ngOnInit(): void {
  
    console.log(this.activatedRoute.snapshot.params.id)
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
    if (this.activatedRoute.snapshot.params.id) {
      this.id = this.activatedRoute.snapshot.params.id;
      this.http.post<any>('http://localhost:8181/api/posts/group/'+this.id,uploadPost).subscribe(data=>{
        console.log(data)
        window.location.reload();
      },err=>{
        console.log(err)
      });
    }
    else {
      this.http.post<any>('http://localhost:8181/api/posts/',uploadPost).subscribe(data=>{
        console.log(data)
        this.ngOnInit()
        window.location.reload();
      },err=>{
        console.log(err)
      });
    }
    
    
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

}
