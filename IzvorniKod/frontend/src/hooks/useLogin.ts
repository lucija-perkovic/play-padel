import { useMutation } from "@tanstack/react-query";
import {User} from "@/models/user";
import {login, register} from "@/services/backend";

interface IFormInput {
    email: string;
    password: string;
}

export const useLogin = () => {
    return useMutation({
        mutationFn: (formData: IFormInput) => login(formData.email, formData.password),
    });
};