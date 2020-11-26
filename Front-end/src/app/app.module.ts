import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { SignupComponent } from './auth/signup/signup.component';
import { ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { LoginComponent } from './auth/login/login.component';
import {NgxWebstorageModule} from 'ngx-webstorage';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToastrModule} from 'ngx-toastr';
import { HomeComponent } from './home/home.component';
import { FooterComponent } from './footer/footer.component';
import { FeedComponent } from './feed/feed.component';
import { UserProfileComponent } from './auth/user-profile/user-profile.component';
import {TokenInterceptor} from './token-interceptor';
import { NewsfeedComponent } from './newsfeed/newsfeed.component';
import { FormsModule } from '@angular/forms';
import { CreatePostComponent } from './post/create-post/create-post.component';
import { ViewPostsComponent } from './post/view-posts/view-posts.component';
import { CommentComponent } from './comment/comment.component';
import { UserDetailsComponent } from './auth/user-details/user-details.component';
import { RouterModule, ROUTES } from '@angular/router';
import { ProfileComponent } from './profile/profile.component';
import { LikeComponent } from './like/like.component';
import { ViewGroupComponent } from './groups/view-group/view-group.component';
import { CreateGroupComponent } from './groups/create-group/create-group.component';
import { ListGroupComponent } from './groups/list-group/list-group.component';
import { JoinRequestsComponent } from './groups/join-requests/join-requests.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { VerificationComponent } from './verification/verification.component';
import { TrainingsComponent } from './trainings/trainings.component';
import {CalendarModule} from 'primeng/calendar';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatSelectCountryModule } from '@angular-material-extensions/select-country'; 
import {MatCheckboxModule} from '@angular/material/checkbox'; 
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CreateCourseComponent } from './courses/create-course/create-course.component';
import { ViewCourseComponent } from './courses/view-course/view-course.component';
import { ConnectionsComponent } from './connections/connections.component';
import { UsersListComponent } from './users-list/users-list.component';
import { HomePageComponent } from './home-page/home-page.component';
import { ServicesComponent } from './services/services.component';
import { ResumeComponent } from './resume/resume.component';
import { CreateJobOfferComponent } from './jobs/create-job-offer/create-job-offer.component';
import { ViewJobsComponent } from './jobs/view-jobs/view-jobs.component';
import { JobDetailsComponent } from './jobs/job-details/job-details.component';
import { JobDescriptionComponent } from './jobs/job-description/job-description.component'; 
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SignupComponent,
    LoginComponent,
    HomeComponent,
    FooterComponent,
    FeedComponent,
    UserProfileComponent,
    NewsfeedComponent,
    CreatePostComponent,
    ViewPostsComponent,
    CommentComponent,
    UserDetailsComponent,
    ProfileComponent,
    LikeComponent,
    ViewGroupComponent,
    CreateGroupComponent,
    ListGroupComponent,
    JoinRequestsComponent,
    NotificationsComponent,
    VerificationComponent,
    TrainingsComponent,
    CreateCourseComponent,
    ViewCourseComponent,
    ConnectionsComponent,
    UsersListComponent,
    HomePageComponent,
    ServicesComponent,
    ResumeComponent,
    CreateJobOfferComponent,
    ViewJobsComponent,
    JobDetailsComponent,
    JobDescriptionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxWebstorageModule.forRoot(),
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    FormsModule,
    CalendarModule,
    MatDatepickerModule,
    MatSelectCountryModule,
    MatCheckboxModule,
    NgbModule
  ],
  providers: [
    {
      provide:HTTP_INTERCEPTORS,
      useClass:TokenInterceptor,
      multi:true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
