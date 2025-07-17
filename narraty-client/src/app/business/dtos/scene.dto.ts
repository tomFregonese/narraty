import {EditChoiceDtoIn, ReadChoiceDtoIn} from './choice.dto';

export interface ReadSceneDtoIn {

    ttl: string;

    txt: string;

    chcs: ReadChoiceDtoIn[];

}

export interface EditSceneDtoIn {

    id: string

    ttl: string;

    txt: string;

    stts: number;

    chcs: EditChoiceDtoIn[];

}