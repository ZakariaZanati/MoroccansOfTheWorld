import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-job-description',
  templateUrl: './job-description.component.html',
  styleUrls: ['./job-description.component.css']
})
export class JobDescriptionComponent implements OnInit {

  id = 1;
  constructor(private router : Router,private route : ActivatedRoute) { }

  ngOnInit(): void {
  }

  viewJobDetails(){
    this.router.navigate([this.id],{relativeTo : this.route})
  }

}
