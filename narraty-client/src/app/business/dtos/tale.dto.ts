import {EditSceneDtoIn} from './scene.dto';

export interface ReadTaleDtoIn {

    id: string;

    title: string;

    description: string;

    createdAt: string;

    updatedAt: string;

    authorId: string;

    playCount: number;

}

export interface EditTaleDtoIn {

    id: string;

    title: string;

    description: string;

    createdAt: string;

    updatedAt: string;

    authorId: string;

    status: number;

    scenes: EditSceneDtoIn[];

    playCount: number;

}