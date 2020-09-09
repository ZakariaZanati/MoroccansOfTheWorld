import { Component, OnInit } from '@angular/core';
import {GroupModel} from '../group-model';
import {GroupService} from '../group.service';
import { Router,ActivatedRoute } from '@angular/router';
import { PostModel } from 'src/app/shared/post-model';

@Component({
  selector: 'app-view-group',
  templateUrl: './view-group.component.html',
  styleUrls: ['./view-group.component.css']
})
export class ViewGroupComponent implements OnInit {

  group : GroupModel;
  id : number;
  posts : Array<PostModel> = [];
  constructor(private groupService : GroupService,private activatedRoute: ActivatedRoute) {

   }

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params.id;
    this.groupService.getGroup(this.id).subscribe(data => {
      this.group = data;
      if (data.imageBytes) {
        this.group.imageBytes = "data:image/jpeg;base64,"+this.group.imageBytes;
      }
    });

    this.groupService.getGroupPosts(this.id).subscribe(data => {
      this.posts = data;
      this.posts.map(post =>{
        const img = post.image;
        post.profileImage = "data:image/jpeg;base64,"+post.profileImage;
        if (img) {
          post.image = "data:image/jpeg;base64,"+post.image;
        }
      });
    });
  }



}
