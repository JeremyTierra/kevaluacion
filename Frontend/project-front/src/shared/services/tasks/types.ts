export type TaskStatus = 'DONE' | 'IN_PROGRESS' | 'PENDING';

export interface Task {
  id: number;
  title: string;
  description: string;
  status: TaskStatus;
  assignedToUserId: number;
  projectId: number;
  dueDate: string;
  createdAt: string;
  updatedAt: string;
}

export interface CreateTaskDto {
  title: string;
  description: string;
  status: TaskStatus;
  assignedToUserId: number;
  projectId: number;
  dueDate: string;
}

export type UpdateTaskDto = Partial<CreateTaskDto>;
