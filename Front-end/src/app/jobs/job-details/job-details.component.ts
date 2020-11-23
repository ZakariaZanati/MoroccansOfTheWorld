import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {JobModel} from '../job.model';
import { JobsService } from '../shared/jobs.service';

@Component({
  selector: 'app-job-details',
  templateUrl: './job-details.component.html',
  styleUrls: ['./job-details.component.css']
})
export class JobDetailsComponent implements OnInit {


  id : number;
  jobOffer : JobModel;

  constructor(private route : ActivatedRoute, private jobsService : JobsService,private router : Router) { }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      this.id = +param.id;
      this.jobsService.getJob(this.id).subscribe(data => {
        this.jobOffer = data;
      })
    })
  }

  applyLink(){
    //this.router.navigate(["www.google.com"]);
    window.location.href = 'http://www.google.com'
  }

}
