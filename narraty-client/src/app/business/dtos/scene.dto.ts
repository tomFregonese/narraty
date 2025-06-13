import {EditChoiceDtoIn, ReadChoiceDtoIn} from './choice.dto';

export interface ReadSceneDtoIn {

    title: string;

    text: string;

    choices: ReadChoiceDtoIn[];

}

export interface EditSceneDtoIn {

    id: string

    title: string;

    text: string;

    status: number;

    choices: EditChoiceDtoIn[];

}