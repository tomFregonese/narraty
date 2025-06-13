import {EditTaleDtoIn} from './tale.dto';

export interface UpdateStatusResultDto {
    tale: EditTaleDtoIn | null,
    errors: string[] | null
}