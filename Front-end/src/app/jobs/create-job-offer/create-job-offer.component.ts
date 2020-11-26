import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from 'src/app/auth/shared/auth.service';
import {JobModel} from '../job.model';
import {JobsService} from '../shared/jobs.service';

@Component({
  selector: 'app-create-job-offer',
  templateUrl: './create-job-offer.component.html',
  styleUrls: ['./create-job-offer.component.css']
})
export class CreateJobOfferComponent implements OnInit {

  @ViewChild('f') jobForm : NgForm;

  submitted = false;
  jobModel : JobModel;

  constructor(private jobsService : JobsService,private authService : AuthService) { }

  ngOnInit() {
    console.log(this.authService.getUserName());
  }

  onSubmit(){
    this.jobModel = this.jobForm.value;
    this.jobModel.creationDate = new Date().toISOString();
    this.jobModel.expirationDate = new Date(this.jobForm.value.expirationDate).toISOString();
    this.jobModel.author = this.authService.getUserName();
    console.log(this.jobModel);
    this.jobsService.createJobOffer(this.jobModel).subscribe(message => {
      console.log(message);
      this.jobForm.resetForm();
    });
  }

}
