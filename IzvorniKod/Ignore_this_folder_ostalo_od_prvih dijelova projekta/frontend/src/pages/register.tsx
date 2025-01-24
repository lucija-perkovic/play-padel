import { FC, useEffect } from "react";
import { TextField, Button, Typography, Container, Stack } from "@mui/material";
import { useForm, SubmitHandler, Controller } from "react-hook-form";
import { useRegister } from "@/hooks/useRegister";
import {redirect} from "next/navigation";
import {router} from "next/client";

// Define the form data type
interface IFormInput {
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
}

const RegisterPage: FC = () => {
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<IFormInput>();

  const mutation = useRegister();

  const onSubmit: SubmitHandler<IFormInput> = (data) => {
    console.log(data);
    // Handle form submission (e.g., send data to API)
    mutation.mutate({
      username: data.username,
      email: data.email,
      password: data.password,
    });
  };

  useEffect(() => {
    if (mutation.isSuccess) {
      // Handle successful registration
      console.log("Registration successful", mutation.data);
      router.push("/profile/me");
    }
  }, [mutation.data, mutation.error, mutation.isError, mutation.isSuccess]);

  return (
    <Container maxWidth="xs">
      <Stack alignItems="center" spacing={2}>
        <Typography variant="h4" gutterBottom>
          Register
        </Typography>

        <form onSubmit={handleSubmit(onSubmit)} style={{ width: "100%" }}>
          <Stack spacing={2}>
            <Controller
              name="username"
              control={control}
              rules={{
                required: "Username is required",
                minLength: {
                  value: 3,
                  message: "Username must be at least 3 characters",
                },
              }}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Username"
                  variant="outlined"
                  fullWidth
                  margin="normal"
                  error={!!errors.username}
                  helperText={errors.username?.message}
                />
              )}
            />

            <Controller
              name="email"
              control={control}
              rules={{
                required: "Email is required",
                pattern: {
                  value: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/,
                  message: "Invalid email address",
                },
              }}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Email"
                  variant="outlined"
                  fullWidth
                  margin="normal"
                  error={!!errors.email}
                  helperText={errors.email?.message}
                />
              )}
            />

            <Controller
              name="password"
              control={control}
              rules={{
                required: "Password is required",
                minLength: {
                  value: 6,
                  message: "Password must be at least 6 characters",
                },
              }}
              render={({ field }) => (
                <TextField
                  {...field}
                  label="Password"
                  type="password"
                  variant="outlined"
                  fullWidth
                  margin="normal"
                  error={!!errors.password}
                  helperText={errors.password?.message}
                />
              )}
            />

            <Stack>
              <Button
                type="submit"
                variant="contained"
                color="primary"
                sx={{ width: "100%" }}
                disabled={Object.keys(errors).length > 0}
              >
                Register
              </Button>
            </Stack>
            <Stack>
              <Typography variant="body2" align="center">
                {mutation.isError && (
                  <Typography
                    color="error"
                    variant="body2"
                    sx={{ marginBottom: 2 }}
                  >
                    User with the same email already exists. Please try again.
                  </Typography>
                )}
              </Typography>
            </Stack>
          </Stack>
        </form>
      </Stack>
    </Container>
  );
};

export default RegisterPage;
