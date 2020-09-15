import { Component, OnInit } from '@angular/core';
import {GroupModel} from '../group-model';
import {GroupResponse} from '../group-response';
import {GroupService} from '../group.service';
import {AuthService} from '../../auth/shared/auth.service'

@Component({
  selector: 'app-list-group',
  templateUrl: './list-group.component.html',
  styleUrls: ['./list-group.component.css']
})
export class ListGroupComponent implements OnInit {

  showForm : boolean = false;
  groups : Array<GroupModel> = [];
  userType : string;
  currentSelectedPage : number = 0;
  pageNumber : number = 0;
  totalPages : number = 0;
  pageIndexes : Array<number> = [];
  name : string = "";

  constructor(private groupService : GroupService,private authService : AuthService) {

    this.userType = authService.getUserType();
    /*
    this.groupService.getAllGroups().subscribe(data => {
      this.groups = data
      this.groups.map(group => {
        const img = group.imageBytes;
        if (img) {
          group.imageBytes = "data:image/jpeg;base64,"+group.imageBytes
        }
      })
    })
    */
   
  }

  ngOnInit(): void {
    this.getPage(0,4,this.name);
    
  }

  addGroup(){
    this.showForm = !this.showForm
  }

  getPage(page : number,size : number,name : string){
    
    console.log(name);
    this.groupService.getGroupsPage(page,size,name).subscribe((response : GroupResponse)=> {
      console.log(response);
      this.groups = response.groups;
      this.groups.map(group => {
        const img = group.imageBytes;
        if (img) {
          group.imageBytes = "data:image/jpeg;base64,"+group.imageBytes
        }
      })
      this.totalPages = response.totalPages;
      this.pageIndexes = Array(this.totalPages).fill(0).map((x,i)=>i);
      this.currentSelectedPage = response.pageNumber;
      console.log(this.groups)
    },error => console.log(error));
  }

  nextClick(){
    if(this.currentSelectedPage < this.totalPages-1){
      this.getPage(++this.currentSelectedPage,4,this.name);
    }  
  }

  previousClick(){
    if(this.currentSelectedPage > 0){
      this.getPage(--this.currentSelectedPage,4,this.name);
    }  
  }

  getPaginationWithIndex(index: number) {
    this.getPage(index, 4,this.name);
  }

  active(index: number) {
    if(this.currentSelectedPage == index ){
      return {
        active: true
      };
    }
  }

  onSearchChange(searchValue: string): void { 
    console.log(searchValue) 
    this.name = searchValue;
    this.getPage(0,4,this.name);
  }

}
