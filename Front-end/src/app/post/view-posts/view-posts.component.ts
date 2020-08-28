import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';
import {PostModel} from '../../shared/post-model';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-view-posts',
  templateUrl: './view-posts.component.html',
  styleUrls: ['./view-posts.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class ViewPostsComponent implements OnInit {

  @Input() posts : PostModel[]; 

  constructor(private domSanitizer: DomSanitizer) {
    
  }

  ngOnInit(): void {
  }

}
