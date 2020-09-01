import { Component, OnInit,Input } from '@angular/core';
import { PostModel } from '../shared/post-model';
import {LikeService} from './like.service';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-like',
  templateUrl: './like.component.html',
  styleUrls: ['./like.component.css']
})
export class LikeComponent implements OnInit {

  @Input() post : PostModel;

  postId : number;
  liked : boolean;
  constructor(private likeService : LikeService) { }

  ngOnInit(): void {
    this.liked = this.post.liked
    console.log(this.liked)
  }

  postLike(){
    this.likeService.postLike(this.post.id).subscribe(data =>{
      this.liked = !this.liked
      if (this.liked) {
        this.post.likeCount = this.post.likeCount+1
      }
      else{
        this.post.likeCount = this.post.likeCount-1
      }
      console.log(data)
    },err => {
      throwError(err);
    })
  }

}
