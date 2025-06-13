import {Injectable} from '@angular/core';
import {UserDto} from '../dtos/user.dto';
import {User} from '../models/user.model';

@Injectable({providedIn: 'root'})
export class UserMapper {

    public dtoToModel(userDto: UserDto): User {
        return {
            id: userDto.id,
            username: userDto.username,
            email: userDto.email,
            createdAt: new Date(userDto.createdAt),
            updatedAt: new Date(userDto.updatedAt),
            experiencePoints: userDto.experiencePoints,
        };
    }

    public modelToDto(user: User): UserDto {
        return {
            id: user.id,
            username: user.username,
            email: user.email,
            createdAt: user.createdAt.toISOString(),
            updatedAt: user.updatedAt.toISOString(),
            experiencePoints: user.experiencePoints,
        };
    }

}
