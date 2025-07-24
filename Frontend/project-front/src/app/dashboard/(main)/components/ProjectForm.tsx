import { Form, useZodForm } from '@/shared/form';
import { Button } from '@/components/atoms';
import { AdvancedInputField } from '@/components/molecules/input/advanced-input';
import { projectSchema, type ProjectFormValues } from '../validators/project.schema';
import { Project } from '@/shared/services/projects/types';

interface ProjectFormProps {
  onSubmit: (data: ProjectFormValues) => Promise<void>;
  initialData?: Project;
  isSubmitting?: boolean;
}

export function ProjectForm({ onSubmit, initialData, isSubmitting }: ProjectFormProps) {
  const form = useZodForm(projectSchema, {
    defaultValues: {
      name: initialData?.name || '',
      description: initialData?.description || '',
    },
  });

  return (
    <Form<ProjectFormValues>
      methods={form}
      onSubmit={onSubmit}
      className="space-y-4"
    >
      <AdvancedInputField
        name="name"
        label="Nombre del proyecto"
        placeholder="Mi proyecto"
      />
      <AdvancedInputField
        name="description"
        label="Descripción"
        placeholder="Describe tu proyecto"
        // multiline
        // rows={4}
      />
      <div className="flex justify-end gap-2">
        <Button type="submit" disabled={isSubmitting}>
          {isSubmitting ? 'Guardando...' : initialData ? 'Actualizar' : 'Crear'}
        </Button>
      </div>
    </Form>
  );
}
