import { z } from 'zod';

export const taskSchema = z.object({
  title: z.string().min(1, 'El título es requerido'),
  description: z.string().min(1, 'La descripción es requerida'),
  status: z.enum(['DONE', 'IN_PROGRESS', 'PENDING']),
  assignedToUserId: z.number().min(1, 'El usuario asignado es requerido'),
  projectId: z.number().min(1, 'El proyecto es requerido'),
  dueDate: z.string().min(1, 'La fecha límite es requerida'),
});

export type TaskFormData = z.infer<typeof taskSchema>;
