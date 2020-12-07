import { Component, OnInit } from '@angular/core';
import {JobModel} from '../../jobs/job.model';
import {JobsService} from '../../jobs/shared/jobs.service';

@Component({
  selector: 'app-prev-jobs',
  templateUrl: './prev-jobs.component.html',
  styleUrls: ['./prev-jobs.component.css']
})
export class PrevJobsComponent implements OnInit {

  jobs : JobModel[] = [];

  constructor(private jobservice : JobsService) {
    
  }

  ngOnInit(): void {
    this.getJobs();
  }

  getJobs(){
    this.jobservice.previewJobs().subscribe((data)=>{
      this.jobs = data;
      console.log(data)
    })
  }
}
