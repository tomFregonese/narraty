import {ReadChoiceDtoIn} from '../dtos/choice.dto';
import {EditChoice} from './choice.model';
import {SceneStatus} from '../enums/scene-status.enum';

export interface ReadScene {

    title: string;

    text: string;

    choices: ReadChoiceDtoIn[];

}

export interface EditScene {

    id: string;

    title: string;

    text: string;

    status: SceneStatus;

    choices: EditChoice[];

}