import {TaleStatus} from '../enums/tale-status.enum';
import {EditScene} from './scene.model';

export interface ReadTale {

    id: string;

    title: string;

    description: string;

    createdAt: Date;

    updatedAt: Date;

    authorId: string;

    playCount: number;

}

export interface EditTale {

    id: string;

    title: string;

    description: string;

    createdAt: Date;

    updatedAt: Date;

    authorId: string;

    status: TaleStatus;

    scenes: EditScene[];

    playCount: number;

}