import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {JobModel} from '../job.model';

@Component({
  selector: 'app-job-description',
  templateUrl: './job-description.component.html',
  styleUrls: ['./job-description.component.css']
})
export class JobDescriptionComponent implements OnInit {

  @Input() jobOffer : JobModel;

  constructor(private router : Router,private route : ActivatedRoute) { }

  ngOnInit(): void {
  }

  viewJobDetails(){
    this.router.navigate([this.jobOffer.id],{relativeTo : this.route})
  }

}
