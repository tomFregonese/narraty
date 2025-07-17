import {Injectable} from '@angular/core';
import {EditTaleDtoIn, ReadTaleDtoIn} from '../dtos/tale.dto';
import {EditTale, ReadTale} from '../models/tale.model';
import {SceneMapper} from './scene.mapper';

@Injectable({providedIn: 'root'})
export class TaleMapper {

    constructor(private sceneMapper: SceneMapper) {}

    public mapReadTaleDtoInToReadTaleModel(taleDto: ReadTaleDtoIn): ReadTale {
        return {
            id: taleDto.id,
            title: taleDto.ttl,
            description: taleDto.dsc,
            createdAt: new Date(taleDto.crtdAt),
            updatedAt: new Date(taleDto.updtAt),
            authorId: taleDto.autrId,
            playCount: taleDto.plyCnt
        };
    }

    mapEditTaleDtoInToEditTaleModel(taleDto: EditTaleDtoIn): EditTale {
        return {
            id: taleDto.id,
            title: taleDto.ttl,
            description: taleDto.dsc,
            createdAt: new Date(taleDto.upddAt),
            updatedAt: new Date(taleDto.crtdAt),
            authorId: taleDto.autrId,
            status: taleDto.stts,
            scenes: taleDto.scns ? taleDto.scns.map(scene => this.sceneMapper.mapEditSceneDtoInToEditScene(scene)) : [],
            playCount: taleDto.plyCnt
        };
    }

}
