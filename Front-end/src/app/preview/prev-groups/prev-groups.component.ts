import { Component, OnInit } from '@angular/core';
import {GroupModel} from '../../groups/group-model';
import {GroupService} from '../../groups/group.service';
import {GroupResponse} from '../../groups/group-response';
import { Router } from '@angular/router';

@Component({
  selector: 'app-prev-groups',
  templateUrl: './prev-groups.component.html',
  styleUrls: ['./prev-groups.component.css']
})
export class PrevGroupsComponent implements OnInit {


  groups : Array<GroupModel> = []

  constructor(private service : GroupService,private router : Router) { }

  ngOnInit(): void {

    this.getPreview(0,3,"",false);

  }

  getPreview(page : number,size : number,name : string,current : boolean){
    
    this.service.getGroupsPage(page,size,name,current).subscribe((response : GroupResponse)=> {
      console.log(response);
      this.groups = response.groups;
    },error => console.log(error));
  }

  showGroup(id : number){
    this.router.navigateByUrl("/group/"+id)
  }

}
