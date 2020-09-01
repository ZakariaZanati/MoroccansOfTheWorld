import { Component, OnInit } from '@angular/core';
import {PostModel} from '../shared/post-model';
import {PostService} from '../shared/post.service';

@Component({
  selector: 'app-newsfeed',
  templateUrl: './newsfeed.component.html',
  styleUrls: ['./newsfeed.component.css']
})
export class NewsfeedComponent implements OnInit {

  posts : Array<PostModel> = [];

  constructor(private postService : PostService) { 
    this.postService.getAllPosts().subscribe(post =>{
      this.posts = post;
      this.posts.map(post =>{
        const img = post.image;
        post.profileImage = "data:image/jpeg;base64,"+post.profileImage;
        if (img) {
          post.image = "data:image/jpeg;base64,"+post.image;
        }
      })
    });
  }

  ngOnInit(): void {
  }

}
