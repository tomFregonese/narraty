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
            title: taleDto.title,
            description: taleDto.description,
            createdAt: new Date(taleDto.createdAt),
            updatedAt: new Date(taleDto.updatedAt),
            authorId: taleDto.authorId,
            playCount: taleDto.playCount
        };
    }

    mapEditTaleDtoInToEditTaleModel(taleDto: EditTaleDtoIn): EditTale {
        return {
            id: taleDto.id,
            title: taleDto.title,
            description: taleDto.description,
            createdAt: new Date(taleDto.createdAt),
            updatedAt: new Date(taleDto.updatedAt),
            authorId: taleDto.authorId,
            status: taleDto.status,
            scenes: taleDto.scenes ? taleDto.scenes.map(scene => this.sceneMapper.mapEditSceneDtoInToEditScene(scene)) : [],
            playCount: taleDto.playCount
        };
    }

}
