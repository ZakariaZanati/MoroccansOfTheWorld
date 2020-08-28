import { Component, OnInit, Input } from '@angular/core';
import { PostModel } from '../shared/post-model';
import { FormGroup, FormControlName, FormControl, Validators } from '@angular/forms';
import {CommentPayload} from './comment.payload';
import {CommentService} from './comment.service';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {

  @Input() post : PostModel;

  id : number;
  commentForm : FormGroup;
  commentPayload : CommentPayload;
  comments: CommentPayload[];

  constructor(private commentService: CommentService) { 

    
    this.commentForm = new FormGroup({
      text: new FormControl('')
    });

    this.commentPayload = {
      text : '',
      postId : this.id    
    }
  }

  ngOnInit(): void {
    this.getCommentsForPost();
  }

  postComment(){
    this.commentPayload.text = this.commentForm.get('text').value;
    if (this.commentPayload.text == '') {
      return;
    }
    this.commentPayload.postId = this.post.id;
    this.commentService.postComment(this.commentPayload).subscribe(data => {
      this.commentForm.get('text').setValue('');
      this.getCommentsForPost();
    },error => {
      throwError(error);
    })
  }

  private getCommentsForPost() {
    this.commentService.getAllCommentsForPost(this.post.id).subscribe(data => {
      this.comments = data;
    }, error => {
      throwError(error);
    });
  }

}
