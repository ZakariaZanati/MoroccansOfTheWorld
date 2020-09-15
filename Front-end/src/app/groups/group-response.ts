import {GroupModel} from './group-model';

export class GroupResponse {
    groups : GroupModel[];
    totalPages: number;
    pageNumber: number;
    pageSize: number;
}