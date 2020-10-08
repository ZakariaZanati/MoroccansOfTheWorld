import {UserModel} from './users-model';

export class UsersResponse {

    users  : UserModel[];
    totalPages: number;
    pageNumber: number;
    pageSize: number;
}