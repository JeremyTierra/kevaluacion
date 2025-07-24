import { httpClient } from '../http-client';
import { Task, CreateTaskDto, UpdateTaskDto } from './types';

const BASE_URL = '/tasks';

export const TaskService = {
  getTasks: (assignedToUserId?: number) =>
    httpClient.get<Task[]>(`${BASE_URL}${assignedToUserId ? `?assignedToUserId=${assignedToUserId}` : ''}`),

  getTask: (id: number) => 
    httpClient.get<Task>(`${BASE_URL}/${id}`),

  createTask: (task: CreateTaskDto) =>
    httpClient.post<Task>(BASE_URL, task),

  updateTask: (id: number, task: UpdateTaskDto) =>
    httpClient.patch<Task>(`${BASE_URL}/${id}`, task),

  deleteTask: (id: number) =>
    httpClient.delete(`${BASE_URL}/${id}`),
};
