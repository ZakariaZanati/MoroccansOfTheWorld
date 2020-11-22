import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import {JobsService} from '../shared/jobs.service';
import {JobModel} from '../job.model';

@Component({
  selector: 'app-view-jobs',
  templateUrl: './view-jobs.component.html',
  styleUrls: ['./view-jobs.component.css']
})
export class ViewJobsComponent implements OnInit {

  @ViewChild('f') jobSearch : NgForm;

  jobs : JobModel[] = [];

  constructor(private jobsService : JobsService) { }

  ngOnInit(): void {
    this.jobsService.getJobs().subscribe( (data : JobModel[]) => {
      this.jobs = data;
    });
  }

  onFindJobs(){

    let name = this.jobSearch.value.search;
    let location = this.jobSearch.value.location;
    this.jobsService.findJobsByNameAndLocation(name,location).subscribe((data : JobModel[]) => {
      this.jobs = data;
    });;
  }
}
