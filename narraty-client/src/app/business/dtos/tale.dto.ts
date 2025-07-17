import {EditSceneDtoIn} from './scene.dto';

export interface ReadTaleDtoIn {

    id: string;

    ttl: string;

    dsc: string;

    crtAt: string;

    updAt: string;

    autrId: string;

    plyCnt: number;

}

export interface EditTaleDtoIn {

    id: string;

    ttl: string;

    dsc: string;

    crtAt: string;

    updAt: string;

    autrId: string;

    stts: number;

    scns: EditSceneDtoIn[];

    plyCnt: number;

}