import {EditSceneDtoIn} from './scene.dto';

export interface ReadTaleDtoIn {

    id: string;

    ttl: string;

    dsc: string;

    crtdAt: string;

    updtAt: string;

    autrId: string;

    plyCnt: number;

}

export interface EditTaleDtoIn {

    id: string;

    ttl: string;

    dsc: string;

    crtdAt: string;

    upddAt: string;

    autrId: string;

    stts: number;

    scns: EditSceneDtoIn[];

    plyCnt: number;

}